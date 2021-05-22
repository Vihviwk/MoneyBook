package it.uninsubria.moneybook


class Transaction(var amount : Float, var category : String, var date : String, var description : String) {

    var id = -1

    constructor() : this(
        0f,
        "",
        "",
        ""
    )
}