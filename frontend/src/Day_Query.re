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
        dateFrom
        dateTo
        exercises {
                id
                plannedSeries {
                    id
                }
                doneSeries {
                    id
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