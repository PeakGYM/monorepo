[@react.component]
let make = (~id as _id) => {
  let (simple, _full) =
    Schedule_Query.use(
      ~from=12312312123.0,
      ~to_=123123123123.0,
      ~clientId=None,
    );

  switch (simple) {
  | Data(data) =>
    <div className=TW.([Padding(P8)] |> make)>
      {data##trainings->Array.map(t => t##id->React.string)->React.array}
      "Schedule"->React.string
    </div>
  | _ => React.null
  };
};