// app/src/main/java/com/example/mcnews/ui/auth/LoginFragment.kt
package com.example.mcnews.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcnews.R
import com.example.mcnews.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val login = binding.etLogin.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            Log.d("LoginFragment", "Login attempt: login=$login")
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.login(login, password) { success ->
                    requireActivity().runOnUiThread {
                        if (success) {
                            Log.d("LoginFragment", "Login successful")
                            findNavController().navigate(R.id.action_login_to_articles)
                        } else {
                            Log.e("LoginFragment", "Login failed")
                            Toast.makeText(requireContext(), "Неверные логин или пароль", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}