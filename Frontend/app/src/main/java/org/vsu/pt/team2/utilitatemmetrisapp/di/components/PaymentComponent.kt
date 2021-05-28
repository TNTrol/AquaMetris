package org.vsu.pt.team2.utilitatemmetrisapp.di.components

import dagger.Subcomponent
import org.vsu.pt.team2.utilitatemmetrisapp.ui.main.HistoryFragment
import org.vsu.pt.team2.utilitatemmetrisapp.ui.main.PaymentFragment

@Subcomponent(modules = [])
interface PaymentComponent {

    fun injectPaymentFragment(paymentFragment: PaymentFragment)

    fun injectHistoryFragment(historyFragment: HistoryFragment)
}