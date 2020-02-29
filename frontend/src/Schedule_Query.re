module Training = [%graphql
  {|
  query Trainings(
    $from: Long!,
    $to_: Long!,
    $clientId: String
    ) {
    workouts(
      from: $from,
      to: $to_,
      clientId: $clientId,
    ) {
        id
        name
        coachId
        clientId
        dateFrom
        dateTo

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