package com.udacity.shoestore.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.data.UserRepositoryImpl
import com.udacity.shoestore.databinding.SignUpFragmentBinding
import com.udacity.shoestore.domain.UserRepository
import com.udacity.shoestore.domain.models.User
import java.lang.RuntimeException

class SignUpFragment: Fragment() {
    private var _binding: SignUpFragmentBinding? = null
    private val binding: SignUpFragmentBinding
        get() = _binding ?: throw RuntimeException("SignUpFragmentBinding is null")

    private val userRepository: UserRepository by lazy { UserRepositoryImpl(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sign_up_fragment,
            container,
            false
        )

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            navigateToWelcomeScreen(false)
        }
        binding.signUpButton.setOnClickListener {
            navigateToWelcomeScreen(true)
        }
    }

    private fun navigateToWelcomeScreen(isNewUser: Boolean) {
        if (validateForm()) {
            val login = binding.signUpLoginEdittext.text.toString()
            val password = binding.signUpPasswordEdittext.text.toString()
            val user = User(login = login, password = password)

            val isSignedIn = if (isNewUser) {
                userRepository.signUp(user)
            } else {
                userRepository.signIn(user)
            }

            if (isSignedIn) {
                findNavController().navigate(
                    SignUpFragmentDirections.signUpFragmentToWelcomeFragment(
                        login,
                        isNewUser
                    )
                )
            } else {
                AlertDialog.Builder(context)
                    .setMessage(R.string.authotization_error)
                    .create()
                    .show()
            }
        } else {
            Toast.makeText(context, getString(R.string.please_check_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val loginField = binding.signUpLoginEdittext.apply { error = null }
        val passwordField = binding.signUpPasswordEdittext.apply { error = null }

        if (loginField.text.isNullOrEmpty()) {
            isValid = false
            loginField.error = getString(R.string.login_cant_be_empy)
        }
        val email = loginField.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            loginField.error = getString(R.string.email_is_incorrect)
        }
        if (passwordField.text.isNullOrEmpty()) {
            isValid = false
            passwordField.error = getString(R.string.password_cant_be_empty)
        }
        if (passwordField.text.length < 6) {
            isValid = false
            passwordField.error = getString(R.string.password_should_have_six_symbols)
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}