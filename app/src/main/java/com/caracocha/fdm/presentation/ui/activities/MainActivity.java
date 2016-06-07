package com.caracocha.fdm.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.caracocha.fdm.R;
import com.caracocha.fdm.domain.executor.impl.ThreadExecutor;
import com.caracocha.fdm.presentation.presenters.MainPresenter;
import com.caracocha.fdm.presentation.presenters.MainPresenter.View;
import com.caracocha.fdm.presentation.presenters.impl.MainPresenterImpl;
import com.caracocha.fdm.storage.WelcomeMessageRepository;
import com.caracocha.fdm.threading.MainThreadImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View {

    @BindView(R.id.welcome_textview)
    TextView mWelcomeTextView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // create a presenter for this view
        mPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new WelcomeMessageRepository()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // let's start welcome message retrieval when the app resumes
        mPresenter.resume();
    }

    @Override
    public void showProgress() {
        mWelcomeTextView.setText("Retrieving...");
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        mWelcomeTextView.setText(message);
    }

    @Override
    public void displayWelcomeMessage(String msg) {
        mWelcomeTextView.setText(msg);
    }
}
