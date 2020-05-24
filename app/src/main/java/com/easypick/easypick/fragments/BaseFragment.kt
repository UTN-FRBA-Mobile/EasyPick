package com.easypick.easypick.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.easypick.easypick.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_settings.*

open class BaseFragment: Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var callbackManager: CallbackManager

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this.activity)
    }

    fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    open fun updateUI(user: FirebaseUser?, signInButton: Button, signOutButton: Button?) {
        hideProgressDialog()
        if (user != null) {
            signInButton.visibility = View.GONE
            if (signOutButton != null) {
                signOutButton.visibility = View.VISIBLE
            }
        } else {
            signInButton.visibility = View.VISIBLE
            if (signOutButton != null) {
                signOutButton.visibility = View.GONE
            }
            if (status != null){
                status.text = getString(R.string.signed_out)
            }
        }
    }

    fun handleFacebookAccessToken(token: AccessToken, signInButton: Button, signOutButton: Button?) {
        showProgressDialog()

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user, signInButton, signOutButton)
                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).
                        show()
                    updateUI(null, signInButton, signOutButton)
                }
                hideProgressDialog()
            }
    }

    fun signOut() {
        auth.signOut()
        LoginManager.getInstance().logOut()
        updateUI(null, buttonLoginWithFacebook, buttonSignOut)
    }

}