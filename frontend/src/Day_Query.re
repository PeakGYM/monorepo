module Training = [%graphql
  {|
  query Trainings(
    $workoutId: String!,
    ) {
    workout(
      workoutId: $workoutId,
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

let use = (~id) => {
  let variables = Training.makeVariables(~workoutId=id, ());

  ApolloHooks.useQuery(~variables, Training.definition);
} /* module Query = ApolloHooks.Query.Make(Training)*/;