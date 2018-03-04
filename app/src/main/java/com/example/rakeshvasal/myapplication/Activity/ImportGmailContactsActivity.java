package com.example.rakeshvasal.myapplication.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.GetAccessToken;
import com.example.rakeshvasal.myapplication.GetterSetter.Contact;
import com.example.rakeshvasal.myapplication.GoogleConstants;
import com.example.rakeshvasal.myapplication.R;
import com.google.android.gms.plus.model.people.Person;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImportGmailContactsActivity extends AppCompatActivity {

    final String TAG = getClass().getName();

    private Dialog auth_dialog;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_gmail_contacts);
        list = (ListView) findViewById(R.id.list);
        launchAuthDialog();
    }

    private void setContactList(List<Contact> contacts) {
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_multiple_choice, contacts);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(adapter);
    }

    private void launchAuthDialog() {
        final Context context = this;
        auth_dialog = new Dialog(context);
        auth_dialog.setTitle("WEB_VIEW");
        auth_dialog.setCancelable(true);
        auth_dialog.setContentView(R.layout.auth_dialog);

        auth_dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(GoogleConstants.OAUTH_URL + "?redirect_uri="
        + GoogleConstants.REDIRECT_URI
                + "&response_type=code&client_id=" + GoogleConstants.CLIENT_ID
                + "&scope=" + GoogleConstants.OAUTH_SCOPE);
        web.setWebViewClient(new WebViewClient() {
            boolean authComplete = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("?code=") && authComplete != true) {
                    Uri uri = Uri.parse(url);
                    String authCode = uri.getQueryParameter("code");
                    authComplete = true;
                    auth_dialog.dismiss();
                    new GoogleAuthToken(context).execute(authCode);
                } else if (url.contains("error=access_denied")) {
                    Log.i("", "ACCESS_DENIED_HERE");
                    authComplete = true;
                    auth_dialog.dismiss();
                }
            }
        });
        auth_dialog.show();
    }

    private class GoogleAuthToken extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private Context context;
 
        public GoogleAuthToken(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Contacting Google ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String authCode = args[0];
            GetAccessToken jParser = new GetAccessToken();
            JSONObject json = jParser.gettoken(GoogleConstants.TOKEN_URL,
                    authCode, GoogleConstants.CLIENT_ID,
                    GoogleConstants.CLIENT_SECRET,
                    GoogleConstants.REDIRECT_URI, GoogleConstants.GRANT_TYPE);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            if (json != null) {
                try {
                    String tok = json.getString("access_token");
                    String expire = json.getString("expires_in");
                    String refresh = json.getString("refresh_token");
                    new GetGoogleContacts(context).execute(tok);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private class GetGoogleContacts extends
            AsyncTask<String, String, List<ContactEntry>> {

        private ProgressDialog pDialog;
        private Context context;
 
        public GetGoogleContacts(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Authenticated. Getting Google Contacts ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            pDialog.show();
        }

        @Override
        protected List<ContactEntry> doInBackground(String... args) {
            String accessToken = args[0];
            ContactsService contactsService = new ContactsService(
                    GoogleConstants.APP);
            contactsService.setHeader("Authorization", "Bearer " + accessToken);
            contactsService.setHeader("GData-Version", "3.0");
            List<ContactEntry> contactEntries = null;
            try {
                URL feedUrl = new URL(GoogleConstants.CONTACTS_URL);
                ContactFeed resultFeed = contactsService.getFeed(feedUrl,
                        ContactFeed.class);
                contactEntries = resultFeed.getEntries();
            } catch (Exception e) {
                pDialog.dismiss();
                Toast.makeText(context, "Failed to get Contacts",
                Toast.LENGTH_SHORT).show();
            }
            return contactEntries;
        }

        @Override
        protected void onPostExecute(List<ContactEntry> googleContacts) {
            if (null != googleContacts && googleContacts.size() > 0) {
                List<Contact> contacts = new ArrayList<Contact>();

                for (ContactEntry contactEntry : googleContacts) {
                    String name = "";
                    String email = "";

                    if (contactEntry.hasName()) {
                        Name tmpName = contactEntry.getName();
                        if (tmpName.hasFullName()) {
                            name = tmpName.getFullName().getValue();
                        } else {
                            if (tmpName.hasGivenName()) {
                                name = tmpName.getGivenName().getValue();
                                if (tmpName.getGivenName().hasYomi()) {
                                    name += " ("
                                    + tmpName.getGivenName().getYomi()
                                            + ")";
                                }
                                if (tmpName.hasFamilyName()) {
                                    name += tmpName.getFamilyName().getValue();
                                    if (tmpName.getFamilyName().hasYomi()) {
                                        name += " ("
                                        + tmpName.getFamilyName()
                                                .getYomi() + ")";
                                    }
                                }
                            }
                        }
                    }
                    List<Email> emails = contactEntry.getEmailAddresses();
                    if (null != emails && emails.size() > 0) {
                        Email tempEmail = (Email) emails.get(0);
                        email = tempEmail.getAddress();
                    }
                    Contact contact = new Contact(name, email
                            /*ConstantUtil.CONTACT_TYPE_GOOGLE*/);
                    contacts.add(contact);
                }
                setContactList(contacts);

            } else {
                Log.e(TAG, "No Contact Found.");
                Toast.makeText(context, "No Contact Found.", Toast.LENGTH_SHORT)
                        .show();
            }
            pDialog.dismiss();
        }

    }

}
