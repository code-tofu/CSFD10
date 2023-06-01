Outputs:

    > > Recieved:{"name":"Benjamin Foo Toon Chian","email":"benjamin.ftc@gmail.com","sauce":"salsa","size":3,"comments":"goodbye","toppings":["seafood","arugula"],"crust":"thin"}

    > > JsonConverted:{"name":"Benjamin Foo Toon Chian","email":"benjamin.ftc@gmail.com","sauce":"salsa","size":3,"comments":"goodbye","toppings":["seafood","arugula"],"crust":"thin"}
    > > ToppingStr:"seafood","arugula"

    > > Request XURL:comments=goodbye&size=3&name=Benjamin Foo Toon Chian&toppings="seafood","arugula"&sauce=salsa&email=benjamin.ftc@gmail.com&thickCrust=false

    > > PostReq:<POST https://pizza-pricing-production.up.railway.app/order,comments=goodbye&size=3&name=Benjamin Foo Toon Chian&toppings="seafood","arugula"&sauce=salsa&email=benjamin.ftc@gmail.com&thickCrust=false,[Content-Type:"application/x-www-form-urlencoded"]>
    > > PricingSvcResp:4e483fa59a,1685598214398,40.000000

    > > FullOrder:PizzaOrder{orderId=4e483fa59a, date=Thu Jun 01 13:43:34 SGT 2023, name=Benjamin Foo Toon Chian, email=benjamin.ftc@gmail.com, sauce=salsa, size=3, thickCurst=false, toppings=["seafood", "arugula"], comments=goodbye, total=40.000000}

    > > Inserting:Document{{_id=4e483fa59a, date=Thu Jun 01 13:43:34 SGT 2023, total=40.0, name=Benjamin Foo Toon Chian, email=benjamin.ftc@gmail.com, sauce=salsa, size=3, comments=goodbye, crust=thin, toppings=["seafood", "arugula"]}}

    get 4e483fa59a
    "{\"orderId\":\"4e483fa59a\",\"date\":\"Thu Jun 01 13:43:34 SGT 2023\",\"name\":\"Benjamin Foo Toon Chian\",\"email\":\"benjamin.ftc@gmail.com\"}"

    {
    "\_id": "4e483fa59a",
    "date": {
    "$date": "2023-06-01T05:43:34.398Z"
    },
    "total": 40,
    "name": "Benjamin Foo Toon Chian",
    "email": "benjamin.ftc@gmail.com",
    "sauce": "salsa",
    "size": 3,
    "comments": "goodbye",
    "crust": "thin",
    "toppings": [
    "\"seafood\"",
    "\"arugula\""
    ]
    }
