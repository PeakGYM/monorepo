open MomentRe;

let startMonth = date => {
  date |> Moment.startOf(`month) |> Moment.valueOf;
};

let endMonth = date => date |> Moment.endOf(`month) |> Moment.valueOf;

let mock = {
  "id": "1",
  "name": "Trener Paweł",
  "trainer": "123",
  "client": "123",
  "dateFrom": Js.Date.now(),
  "dateTo": Js.Date.now(),
  "exercises": [|{"id": 1}|],
  "muscleGroup": "Back",
};

type muscleGroup =
  | Chest
  | Back
  | Shoulders
  | Arms
  | Legs;

let toColor =
  fun
  | "Back" => "#e53e3e"
  | "Shoulders" => "#38a169"
  | "Arms" => "#3182ce"
  | "Legs" => "#d69e2e"
  | _ => "transparent";

let renderTraining = (~date, ~workout) => {
  let from =
    workout##dateFrom
    |> Js.Date.fromFloat
    |> MomentRe.momentWithDate
    |> Moment.startOf(`day)
    |> Moment.valueOf;

  date |> Moment.startOf(`day) |> Moment.valueOf === from;
};

[@react.component]
let make = () => {
  let (from, setFrom) =
    React.useState(() => startMonth(MomentRe.momentNow()));
  let (to_, setTo) = React.useState(() => endMonth(MomentRe.momentNow()));

  let (data, full) = Schedule_Query.use(~from, ~to_, ~clientId=None);

  <div className=TW.([Padding(P8)] |> make)>
    <Calendar
      dateCellRender={date => {
        let hasTrainig = renderTraining(~date, ~workout=mock);
        switch (data) {
        | Data(_data) => <Text content="Error" />
        | Error(_) =>
          let ms = date |> Moment.valueOf |> Js.Float.toString;

          hasTrainig
            ? <div
                onClick={_ => Router.go(Router.Day(ms))}
                style={ReactDOMRe.Style.make(
                  ~background=toColor(mock##muscleGroup),
                  ~width="24px",
                  ~height="24px",
                  ~borderRadius="50%",
                  (),
                )}
              />
            : React.null;
        | _ => React.null
        };
      }}
      onChange={date => {
        setTo(_ => date |> endMonth);
        setFrom(_ => date |> startMonth);
      }}
    />
    {full.loading ? <Loader /> : React.null}
  </div>;
};