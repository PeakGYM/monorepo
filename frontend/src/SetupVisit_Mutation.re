module AddTestWorkout = [%graphql
  {|
  mutation AddTestWorkout(
    $from: Long!,
    $clientId: String!
    $coachId: String

    ) {
    addTestWorkout(
      from: $from,
      clientId: $clientId
      coachId: $coachId
    ) {
        id
        name
        coachId
        clientId
        dateFrom @bsDecoder(fn: "Time.fromJSON")
        dateTo  @bsDecoder(fn: "Time.fromJSON")
        muscleGroup
        exercises {
                id
                plannedSeries {
                     id
                    reps
                    rest
                    weight
                }
                doneSeries {
                    id
                    reps
                    rest
                    weight
                }
                restAfter
                exercise {
                    id
                    imgurl
                    name
                }
        }
    }
  }
|}
];

let use = (~from, ~clientId) => {
  let variables = AddTestWorkout.makeVariables(~from, ~clientId, ());

  ApolloHooks.useMutation(~variables, AddTestWorkout.definition);
} /* module Query = ApolloHooks.Query.Make(Training)*/;