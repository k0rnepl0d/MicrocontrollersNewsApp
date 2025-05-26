// app/src/main/java/com/example/mcnews/ui/auth/RegisterFragment.kt
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
import com.example.mcnews.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val login = binding.etLogin.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            Log.d("RegisterFragment", "Register attempt: login=$login, firstName=$firstName, lastName=$lastName")
            if (login.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                Log.w("RegisterFragment", "Empty fields detected")
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.register(login, password, firstName, lastName) { success ->
                    requireActivity().runOnUiThread {
                        if (success) {
                            Log.d("RegisterFragment", "Registration successful")
                            Toast.makeText(requireContext(), "Регистрация успешна, войдите", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_register_to_login)
                        } else {
                            Log.e("RegisterFragment", "Registration failed")
                            Toast.makeText(requireContext(), "Ошибка регистрации: проверьте уникальность логина и email", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}