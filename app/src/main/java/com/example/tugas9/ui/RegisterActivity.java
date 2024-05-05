package com.example.tugas9.ui;


public class RegisterActivity extends AppCompatActivity {
    String Username, Name, Password;
    EditText etUsernameRegister, etNameRegister, etPasswordRegister;
    Button btnRegister;
    TextView tvAlreadyHave;
    ApiInterfaces apiInterfaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etUsernameRegister = findViewById(R.id.etUsernameRegister);
        etNameRegister = findViewById(R.id.etNameRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        tvAlreadyHave = findViewById(R.id.tvAlreadyHave);
        btnRegister = findViewById(R.id.btnRegister);

        tvAlreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = etUsernameRegister.getText().toString();
                Name = etNameRegister.getText().toString();
                Password = etPasswordRegister.getText().toString();
                register(Username, Name, Password);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void register(String username, String name, String password) {

        apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Register> call = apiInterfaces.registerResponse(username, name, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
