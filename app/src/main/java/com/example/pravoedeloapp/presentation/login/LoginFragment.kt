package com.example.pravoedeloapp.presentation.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.pravoedeloapp.App
import com.example.pravoedeloapp.R
import com.example.pravoedeloapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    @javax.inject.Inject
    lateinit var vmFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private var isCodePanelAnimating: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        (requireContext().applicationContext as App).appComponent.inject(this)

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]
        binding.numberField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if ((p0?.toString()?.length ?: -1) <= 1 && binding.codePanel.height != 0 && !isCodePanelAnimating) {
                    viewModel.onEvent(LoginEvent.CodePanelStateChanged(true))
                }
                viewModel.onEvent(LoginEvent.PhoneNumberChanged(p0.toString()))
            }
        })
        binding.codeField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.onEvent(LoginEvent.CodeChanged(p0.toString()))
            }
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authEvents.collect { event ->
                when (event) {
                    is LoginViewModel.AuthEvent.Success -> {
                        val bundle = bundleOf("login" to (viewModel.state.value?.phoneNumber?:""), "token" to (event.token))
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_loginFragment_to_homeFragment, bundle)
                    }
                    LoginViewModel.AuthEvent.Error ->{
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.not_all_fields_correct_warning),
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner) {
            it?.let {
                binding.numberFieldLayout.error = it.phoneNumberError
                binding.codeFieldLayout.error = it.codeError
                if(it.codeError!=null){
                    binding.resendButton.isEnabled = true
                }
            }
        }
        binding.resendButton.setOnClickListener {
            viewModel.onEvent(LoginEvent.RegenerateCode)
        }
        viewModel.isCodeSent.observe(viewLifecycleOwner) { value ->
            value?.let { _value ->
                val animDuration = 600L

                val expandAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = animDuration
                    addUpdateListener {
                        val progress = it.animatedValue as Float
                        binding.codePanel.measure(
                            View.MeasureSpec
                                .makeMeasureSpec((binding.codePanel.parent as View).measuredWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec
                                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        )
                        val wrapContentHeight = binding.codePanel.measuredHeight
                        binding.codePanel.updateLayoutParams {
                            height = (wrapContentHeight * progress).toInt()
                        }
                    }
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)
                            isCodePanelAnimating = true
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            isCodePanelAnimating = false
                        }
                    })
                }
                when {
                    _value -> {
                        expandAnimator.reverse()
                        binding.confirmButton.text = getString(R.string.send_code)
                    }
                    else -> {
                        expandAnimator.start()
                        binding.confirmButton.text = getString(R.string.sign_in)
                    }
                }
            }
        }
        binding.confirmButton.setOnClickListener {
            if ((binding.numberField.text?.length ?: 0) < 11) {
                binding.numberFieldLayout.error = getString(R.string.number_format_error)
                return@setOnClickListener
            }
            if (viewModel.isCodeSent.value == false) {
                viewModel.onEvent(LoginEvent.GetToken)
            } else {
                viewModel.onEvent(LoginEvent.GetCode)
            }
        }
        return binding.root
    }
}
// Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_homeFragment)
