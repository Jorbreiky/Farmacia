package sms.vaio.pedidomedicina;

import android.os.AsyncTask;
import android.util.Log;

public class EmailSender extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... data) {

        String emailSenderAddress=(String)data[0];
        String emailSenderPassword=(String)data[1];
        String recipients=(String)data[2];
        String subject=(String)data[3];
        String comments=(String)data[4];
        String pictureFileName=(String)data[5];

        Email m = new Email(emailSenderAddress,emailSenderPassword);

        m.setTo(recipients);
        m.setFrom(emailSenderAddress);
        m.setSubject(subject);
        m.setBody(comments);

        try {
            m.setPictureFileName(pictureFileName);
            return m.send();
        }
        catch (Exception e ) {
            Log.e("JMA",e.toString());
            throw new RuntimeException("Bang");
        }


    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}
