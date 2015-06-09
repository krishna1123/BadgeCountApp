package com.example.krishna.badgecountapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.krishna.badgecountapp.R;

/**
 * Created by krishna on 20/5/15.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        WebView webView = (WebView) findViewById(R.id.wv_html);


//    private void performFacebookLogin()
//    {
//        Log.d("FACEBOOK", "performFacebookLogin");
//        final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("email"));
//        Session openActiveSession = Session.openActiveSession(this, true, new Session.StatusCallback()
//        {
//            @Override
//            public void call(Session session, SessionState state, Exception exception)
//            {
//                Log.d("FACEBOOK", "call");
//                if (session.isOpened() && !isFetching)
//                {
//                    Log.d("FACEBOOK", "if (session.isOpened() && !isFetching)");
//                    isFetching = true;
//                    session.requestNewReadPermissions(newPermissionsRequest);
//                    Request getMe = Request.newMeRequest(session, new GraphUserCallback()
//                    {
//                        @Override
//                        public void onCompleted(GraphUser user, Response response)
//                        {
//                            Log.d("FACEBOOK", "onCompleted");
//                            if (user != null)
//                            {
//                                Log.d("FACEBOOK", "user != null");
//                                org.json.JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
//                                String email = graphResponse.optString("email");
//                                String id = graphResponse.optString("id");
//                                String facebookName = user.getUsername();
//                                if (email == null || email.length() < 0)
//                                {
//                                    Logic.showAlert(
//                                            ActivityLogin.this,
//                                            "Facebook Login",
//                                            "An email address is required for your account, we could not find an email associated with this Facebook account. Please associate a email with this account or login the oldskool way.");
//                                    return;
//                                }
//                            }
//                        }
//                    });
//                    getMe.executeAsync();
//                }
//                else
//                {
//                    if (!session.isOpened())
//                        Log.d("FACEBOOK", "!session.isOpened()");
//                    else
//                        Log.d("FACEBOOK", "isFetching");
//
//                }
//            }
//        });

    }
}
