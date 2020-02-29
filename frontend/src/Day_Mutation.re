module UpdateWorkout = [%graphql
  {|
  mutation UpdateWorkout(
    $training: WorkoutInput!,
    ) {
    updateWorkout(
      training: $training,
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

let use = ((), ~training) => {
  let variables = UpdateWorkout.makeVariables(~training, ());

  ApolloHooks.useMutation(~variables, UpdateWorkout.definition);
} /* module Query = ApolloHooks.Query.Make(Training)*/;