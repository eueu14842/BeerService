package com.example.beerservice.app.screens.main.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.beerservice.R
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.accounts.entities.UserEditData
import com.example.beerservice.app.screens.base.BaseFragment
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.ViewModelFactory
import com.example.beerservice.app.utils.observeEvent
import com.example.beerservice.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    lateinit var binding: FragmentEditProfileBinding
    override val viewModel: EditProfileVieModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        listenInitialEditEvent()
        binding.saveButton.setOnClickListener { onSaveButtonPressed() }
        binding.logoutButton.setOnClickListener { logout() }

    }

    private fun onSaveButtonPressed() {
        val id = viewModel.userIdState.value
        val userEditData = UserEditData(
            binding.editTextName.text.toString(),
            binding.editMail.text.toString(),
            binding.editTel.text.toString(),
            binding.birthdayEditText.text.toString(),
            binding.countryEditText.text.toString(),
            binding.cityEditText.text.toString()
        )
        viewModel.updateAccount(id!!, userEditData)
    }


    private fun listenInitialEditEvent() =
        viewModel.initialEditEvent.observeEvent(viewLifecycleOwner) { user ->
            with(binding) {
                editTextName.setText(user.userName)
                editMail.setText(user.mail)
                editTel.setText(user.telephoneNumber)
                birthdayEditText.setText(user.birthday)
                countryEditText.setText(user.country)
                cityEditText.setText(user.city)
            }
        }

/*    private fun observeProfile() {
        val user = with(binding) {
            viewModel.accountsRepository.getAccount().map { user ->
                editTextName.setText(user.userName)
                editMail.setText(user.mail)
                editTel.setText(user.telephoneNumber)
                birthdayEditText.setText(user.birthday)
                countryEditText.setText(user.country)
                cityEditText.setText(user.city)
            }
        }
    }*/

}