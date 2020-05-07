package com.easypick.easypick.fragments

import android.app.ProgressDialog
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.easypick.easypick.R

open class BaseFragment: Fragment() {
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
    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }
}