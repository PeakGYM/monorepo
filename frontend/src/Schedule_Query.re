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
        dateFrom @bsDecoder(fn: "Time.fromJSON")
        dateTo  @bsDecoder(fn: "Time.fromJSON")
        muscleGroup

    }
  }
|}
];

let use = (~from, ~to_) => {
  let variables =
    Training.makeVariables(
      ~from=from->Time.toJSON,
      ~to_=to_->Time.toJSON,
      ~clientId="1",
      (),
    );

  ApolloHooks.useQuery(
    ~fetchPolicy=NetworkOnly,
    ~variables,
    Training.definition,
  );
} /* module Query = ApolloHooks.Query.Make(Training)*/;