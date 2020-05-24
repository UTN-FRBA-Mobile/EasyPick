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
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: BaseFragment() {
    private var listener: FragmentHome.OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLoginWithFacebook.fragment = this
        buttonLoginWithFacebook.setReadPermissions("email", "public_profile")

        buttonLoginWithFacebook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                updateUI(null, buttonLoginWithFacebook, buttonSignOut)
            }

            override fun onError(error: FacebookException) {
                updateUI(null, buttonLoginWithFacebook, buttonSignOut)
            }
        })

        buttonSignOut.setOnClickListener {
            signOut()
        }

        updateUI(auth.currentUser, buttonLoginWithFacebook, buttonSignOut)
    }

    override fun updateUI(user: FirebaseUser?, signInButton: Button, signOutButton: Button?) {
        super.updateUI(user, signInButton, signOutButton)
        if (user != null){
            status.text = "Conectado como " + user.email
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
        fun newInstance() = SettingsFragment()

        private const val TAG = "SettingsFragment"
    }
}