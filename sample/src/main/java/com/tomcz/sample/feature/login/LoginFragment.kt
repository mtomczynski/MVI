package com.tomcz.sample.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.tomcz.mvi.common.clicks
import com.tomcz.mvi.common.onProcessor
import com.tomcz.sample.databinding.FragmentLoginBinding
import com.tomcz.sample.feature.login.state.LoginEffect
import com.tomcz.sample.feature.login.state.LoginEvent
import com.tomcz.sample.feature.login.state.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onProcessor(
            lifecycleState = Lifecycle.State.RESUMED,
            processor = viewModel::processor,
            viewEvents = ::viewEvents,
            onState = ::render,
            onEffect = ::trigger
        )
        return FragmentLoginBinding.inflate(layoutInflater).also { binding = it }.root
    }

    private fun render(state: LoginState) = with(state) {
        binding!!.progress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun viewEvents(): List<Flow<LoginEvent>> = listOf(
        binding!!.register.clicks().map { LoginEvent.GoToRegister },
        binding!!.login.clicks().map {
            LoginEvent.LoginClick(
                binding!!.email.text.toString(),
                binding!!.pass.text.toString()
            )
        }
    )

    private fun trigger(effect: LoginEffect): Unit = when (effect) {
        LoginEffect.GoToHome -> openHome()
        LoginEffect.GoToRegister -> openRegister()
        LoginEffect.ShowError -> showErrorToast()
    }

    private fun openRegister() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        )
    }

    private fun openHome() {
        /* TODO */
    }

    private fun showErrorToast() {
        /* TODO */
    }
}
