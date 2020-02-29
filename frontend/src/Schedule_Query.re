module Training = [%graphql
  {|
  query Trainings(
    $from: Long!,
    $to_: Long!,
    $clientId: String
    ) {
    trainings(
      from: $from,
      to: $to_,
      clientId: $clientId,
    ) {
        id
        name
        trainer
        client
        dateFrom
        dateTo
        exercises {
            id
        }
    }
  }
|}
];

let use = (~from, ~to_, ~clientId) => {
  let variables =
    Training.makeVariables(
      ~from=from->Time.toJSON,
      ~to_=to_->Time.toJSON,
      ~clientId?,
      (),
    );

  ApolloHooks.useQuery(~variables, Training.definition);
} /* module Query = ApolloHooks.Query.Make(Training)*/;

let mock = [|
  {
    "id": "1",
    "name": "Trener Paweł",
    "trainer": "123",
    "client": "123",
    "dateFrom": Js.Date.now(),
    "dateTo": Js.Date.now(),
    "exercises": [|{"id": 1}|],
  },
|];