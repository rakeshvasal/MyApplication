package com.example.rakeshvasal.myapplication.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.Activity.MainActivity;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.BuildConfig;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.FirebaseCloudMessaging.NotificationUtils;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ItextFragment extends BaseFragment {


    private static String FILE = "FirstPdf";
    String fpath;
    String dirpath = Environment.getExternalStorageDirectory() + "/ItextPDF/";
    FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mEventsDatabase, ref, eventref;
    final java.util.List<Events> mEventsEntries = new ArrayList<>();

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public ItextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_itext, container, false);
        /*FILE=*/
        String.format(FILE = "" + SystemClock.currentThreadTimeMillis(), "MM/dd/yyyy");
        Log.d("filename", FILE);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mEventsDatabase = mFirebaseInstance.getReference();
        ref = mEventsDatabase.child("events");
        showProgressDialog();
        createFile();
        fetchallevents();
        return root;
    }

    private void createDocument() {
        try {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fpath));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();

            closeProgressDialog();
            sendNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", catFont));
        preface.add(new Paragraph("Hello, This is a document", catFont));
        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Events", catFont);
        anchor.setName("Name of Events");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        Paragraph subPara = new Paragraph("Category 1", subFont);

        Section subCatPart = catPart.addSection(subPara);

        /*subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));*/

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private void createList(Section subCatPart) {
        List list = new List(true, false, 20);
        for (int i = 0; i < mEventsEntries.size(); i++) {
            list.add(new ListItem(mEventsEntries.get(i).eventName));
        }
        /*list.add(new ListItem("First point is this "));
        list.add(new ListItem("Second point is this "));
        list.add(new ListItem("Third point is this "));*/
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private File createFile() {
        /*String.format(FILE = "" + SystemClock.currentThreadTimeMillis(), "MM/dd/yyyy");
        Log.d("filename", FILE);*/
        File file = null;
        File storageDir = new File(dirpath);


        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        if (storageDir.exists()) {
            fpath = storageDir + "/" + FILE + ".pdf";
            file = new File(fpath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private void sendNotification() {
        try {
            Uri file;
            File mediaFile = new File(fpath);
            if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
                file = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", mediaFile);
            } else {
                file = Uri.fromFile(mediaFile);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(file, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            NotificationUtils notificationUtils = new NotificationUtils(getActivity());
            notificationUtils.showNotificationMessage("File Created", "PDF created", Utils.getSystemTime(), intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchallevents() {
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Events events = eventsnapshot.getValue(Events.class);
                        mEventsEntries.add(events);

                    }
                    createDocument();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("eventsdatabaseerror", databaseError.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
