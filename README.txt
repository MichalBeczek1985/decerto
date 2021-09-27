Aplikacja zbudowana na springboocie, wystarczy uruchomic metode main klacy DecertoApplication

Endpoity dla zapytan GET, PUT, POST - "/quote"
Endpoint dla zapytania DELETE - "/quote/{id}" - gdzie id to id postu do skasowania

przykładowe body dla zapytania POST:
{
"author" : {
"firstName":"michal2",
"lastName":"beczek2"
},
"quote":"lorem ipsum2"
}

przykładowe body dla zapytania PUT:
{"id":1,
"quote":"lorem"
}

