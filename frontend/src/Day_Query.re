type serie = {
  id: string,
  reps: int,
  rest: int,
  weight: option(int),
};

type exercise = {
  id: string,
  imgurl: string,
  name: string,
};

type e = {
  id: string,
  doneSeries: array(serie),
  plannedSeries: array(serie),
  restAfter: int,
  exercise: option(exercise),
};
type muscleGroup = [ | `Arms | `Back | `Chest | `Legs | `Shoulders];

type workout = {
  id: string,
  name: string,
  coachId: option(string),
  clientId: string,
  dateFrom: float,
  dateTo: float,
  muscleGroup: array(muscleGroup),
  exercises: array(e),
};

module Training = [%graphql
  {|
  query Trainings(
    $workoutId: String!,
    ) {
    workout(
      workoutId: $workoutId,
    ) @bsRecord  {
        id
        name
        coachId
        clientId
        dateFrom @bsDecoder(fn: "Time.fromJSON")
        dateTo  @bsDecoder(fn: "Time.fromJSON")
        muscleGroup
        exercises @bsRecord {
                id
                plannedSeries @bsRecord {
                     id
                    reps
                    rest
                    weight
                }
                doneSeries @bsRecord {
                    id
                    reps
                    rest
                    weight
                }
                restAfter
                exercise @bsRecord {
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