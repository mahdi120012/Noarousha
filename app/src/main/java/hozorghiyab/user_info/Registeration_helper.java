package hozorghiyab.user_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import com.hozorghiyab.R;


public class Registeration_helper extends AsyncTask<Void, Void, String> {

    Context        c;
    String         urlAddress;
    EditText       etName, etPhoneNumber, etPassword;
    TextView txState;
    ProgressDialog pd;
    User           user;
    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;


    public Registeration_helper(Context c, String urlAddress, EditText etName, EditText etPhoneNumber
                                    , EditText etPassword, TextView txState,ConstraintLayout constraintLayout) {

        this.c = c;
        this.urlAddress = urlAddress;
        this.etName = etName;
        this.etPhoneNumber = etPhoneNumber;
        this.etPassword = etPassword;
        this.txState = txState;
        this.constraintLayout = constraintLayout;

        user = new User();
        user.setName(etName.getText().toString());
        user.setPhoneNumber(etPhoneNumber.getText().toString());
        user.setPassword(etPassword.getText().toString());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("عضویت کاربر");
        pd.setMessage("درحال عضویت ... لطفا صبر کنید");
        pd.show();
    }


    @Override
    protected String doInBackground(Void... params) {
        return this.registerUser();
    }


    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        pd.dismiss();
        if (response.startsWith("Error")) {
            display(response);

        } else {

            if (response.startsWith(ErrorTracker.USER_EXIST)) {
                //display(response);
                etPhoneNumber.setBackgroundResource(R.drawable.et_border_red);
                txState.setText("این شماره همراه از قبل وجود دارد.");
                txState.setVisibility(View.VISIBLE);

                constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(R.id.etPassInRegisterPage,ConstraintSet.TOP,R.id.etPhoneNumberInRegisterPage,ConstraintSet.TOP,200);
                constraintSet.applyTo(constraintLayout);


            }

            else if (response.startsWith(ErrorTracker.SUCCESS)) {
                etName.setText("");
                etPhoneNumber.setText("");
                etPassword.setText("");

                display(response);
                Toast.makeText(c, "عضویت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                etPhoneNumber.setBackgroundResource(R.xml.edittext_style);



            }
        }
    }


    private void display(String txt) {
        //Toast.makeText(c, txt, Toast.LENGTH_LONG).show();
        /*if(txt.contains("user exist already,sorry")){
            txState.setText("شماره همراه از قبل وجود دارد");
        }*/


    }


    private String registerUser() {
        Object connection = Connector.connect(urlAddress);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();

        }

        try {

            HttpURLConnection con = (HttpURLConnection) connection;
            OutputStream os = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            String registerationData = new Registeraton_data(user).packregisterationData();
            if (registerationData.startsWith("Error")) {
                return registerationData;

            }

            bw.write(registerationData);
            bw.flush();
            bw.close();
            os.close();

            int responseCode = con.getResponseCode();
            if (responseCode == con.HTTP_OK) {
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    response.append(line + "\n");
                }

                br.close();
                is.close();

                return response.toString();
            } else {

                return ErrorTracker.RESPONSE_ERROR + String.valueOf(responseCode);

            }

        }
        catch (IOException e) {
            e.printStackTrace();
            return ErrorTracker.IO_ERROR;

        }

    }
}
