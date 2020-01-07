package bd.com.nabdroid.openvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText userNameET, mobileNumberET, emailET, passwordET, confirmPasswordET;
    private Button registerBTN;
    String userName, mobileNumber, email, password, confirmPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameET.getText().toString().trim();
                mobileNumber = mobileNumberET.getText().toString().trim();
                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                confirmPassword = confirmPasswordET.getText().toString().trim();
                if (confirmPassword.equals(password)) {
                    createUser(email, password);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Password didn't mached", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        }
                        else {
                            String error = task.getException().toString().trim();
                            Toast.makeText(SignUpActivity.this, "Message: "+error+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void init() {
        userNameET = findViewById(R.id.userNameETSU);
        mobileNumberET = findViewById(R.id.mobileNumberETSU);
        emailET = findViewById(R.id.emailETSU);
        passwordET = findViewById(R.id.passwordETSU);
        confirmPasswordET = findViewById(R.id.confirmPasswordETSU);
        registerBTN = findViewById(R.id.registerBTNSU);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
