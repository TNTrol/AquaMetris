package org.vsu.pt.team2.utilitatemmetrisapp.repository

import org.vsu.pt.team2.utilitatemmetrisapp.models.PaymentData


class PaymentRepo {
    //todo если будет реальный репозиторий с бд, то сделать:
    /*
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
         withContext(dispatcher) {
            //code
         }
     */

    private val payments = hashSetOf<PaymentData>()

    fun payments(): HashSet<PaymentData> = payments

    fun clear() {
        payments.clear()
    }

    fun addPayment(acc: PaymentData) {
        payments.add(acc)
    }

    fun addPayments(accs: List<PaymentData>) {
        payments.addAll(accs)
    }

    fun deletePayment(acc: PaymentData) {
        payments.remove(acc)
    }

}