package ru.chieffly.memoscope.di


import dagger.Component
import ru.chieffly.memoscope.view.login.LoginPresenter
import ru.chieffly.memoscope.view.main.fragments.AddPresenter
import ru.chieffly.memoscope.view.main.fragments.DashPresenter
import ru.chieffly.memoscope.view.main.LogoutDialogPresenter
import ru.chieffly.memoscope.view.main.fragments.ProfilePresenter
import ru.chieffly.memoscope.view.main.adapters.MemRecyclerAdapter
import ru.chieffly.memoscope.view.membrowser.MemBrowserPresenter
import javax.inject.Singleton

@Component(modules = [AppModule::class, RemoteModule::class])
@Singleton
interface AppComponent {

    fun inject(viewModel: LoginPresenter)
    fun inject(viewModel: MemBrowserPresenter)
    fun inject(viewModel: AddPresenter)
    fun inject(viewModel: DashPresenter)
    fun inject(viewModel: LogoutDialogPresenter)
    fun inject(viewModel: ProfilePresenter)
    fun inject(viewModel: MemRecyclerAdapter.ViewHolder)
}