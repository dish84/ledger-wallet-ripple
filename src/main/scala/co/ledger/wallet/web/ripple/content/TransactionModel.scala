package co.ledger.wallet.web.ripple.content

import java.util.Date

import co.ledger.wallet.core.wallet.ripple.{Block, RippleAccount, Transaction, XRP}
import co.ledger.wallet.web.ripple.core.database.Model

/**
  *
  * TransactionModel
  * ledger-wallet-ripple-chrome
  *
  * Created by Pierre Pollastri on 14/06/2016.
  *
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 Ledger
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  *
  */
class TransactionModel extends Model("transactions") {
  val hash = string("hash").unique().index().encrypted()
  val height = long("height")
  val receivedAt = date("receivedAt").index()
  val account = string("account")
  val fee = long("fee")
  val value = long("value")
  val destination = string("destination")

  def proxy: Transaction = {
    new Transaction {

      override def height: Option[Long] = None

      override def account: RippleAccount = RippleAccount(TransactionModel
        .this.account().get)

      override def destination: RippleAccount = RippleAccount(TransactionModel
        .this.destination().get)

      override def hash: String = TransactionModel.this.hash().get

      override def value: XRP = XRP(TransactionModel.this.value().get)

      override def fee: XRP = XRP(TransactionModel.this.fee().get)

      override def receivedAt: Date = new Date(TransactionModel.this.receivedAt().get
        .getTime().toLong)
    }
  }
}
