package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.easypick.easypick.R
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_force_auth.*

class ForceAuthFragment: BaseAuthFragment() {
    private var listener: FragmentHome.OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_force_auth, container, false)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser, buttonContinueWithFacebook, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonContinueWithFacebook.fragment = this
        buttonContinueWithFacebook.setReadPermissions("email", "public_profile")

        buttonContinueWithFacebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken, buttonContinueWithFacebook, null)
            }

            override fun onCancel() {
                updateUI(null, buttonContinueWithFacebook, null)
            }

            override fun onError(error: FacebookException) {
                updateUI(null, buttonContinueWithFacebook, null)
            }
        })
    }

    override fun updateUI(user: FirebaseUser?, signInButton: Button, signOutButton: Button?) {
        super.updateUI(user, signInButton, signOutButton)
        if (user != null){
            listener?.showFragment(PagoFragment())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentHome.OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StatusUpdate.
         */
        @JvmStatic
        fun newInstance() = ForceAuthFragment()

        private const val TAG = "SocialAuth"
    }
}