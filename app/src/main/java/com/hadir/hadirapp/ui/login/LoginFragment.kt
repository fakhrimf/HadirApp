package com.hadir.hadirapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.hadir.hadirapp.*
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {


    private val vm by lazy {
    }

    companion object {
        val RC_SIGN_IN: Int = 1
        val user = FirebaseAuth.getInstance().currentUser

    }


    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var TextView: TextView
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    lateinit var mDatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        val  user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            startActivity<MainActivity>(requireContext())
            (activity as LoginActivity).finish()

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        configureGoogleSignIn()
        setUp()
        setUpWithEmail()

        loginBtn.setOnClickListener {
            val intent = Intent(requireContext(), StatisticsActivity::class.java)
            startActivity(intent)
        }


    }

    private  fun configureGoogleSignIn(){
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(),
            mGoogleSignInOptions
        )
    }

    private  fun signIn(){
        val signIntent=Intent (mGoogleSignInClient.signInIntent)
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    private  fun setUp(){
        login_with_google.setOnClickListener {
            signIn()
        }
    }

    //setup button with email
    private fun setUpWithEmail(){
        loginBtn.setOnClickListener {
            loginWithEmail()
        }
    }



    //login with email
    private fun loginWithEmail(){
        val email =  email.toString()
        val password = passwordField.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    val uId = user!!.uid

                    if(user.email == email ) {
                        mDatabase.child(uId).child("email").setValue(email)
                        startActivity<MainActivity>(requireContext())
                        Toast.makeText(requireContext(), "Login Success",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }

    //authentication firebase with google
    private  fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acct.idToken,null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(MainActivity.getLaunchIntent(requireContext()))
            }else{
                Toast.makeText(requireContext(),"Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            }catch (e: ApiException){
                e.printStackTrace()
                Toast.makeText(requireContext(),"Google Sign In Failed"+ e,Toast.LENGTH_LONG)
                    .show()

            }

        }
    }
}
