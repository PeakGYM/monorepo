open BsRecharts;

[@react.component]
let make = () => {
  let (stats, _full) = Statistics_Query.use(~clientId="1");

  switch (stats) {
  | Data(result) =>
    let data =
      result##getAllFor
      ->List.fromArray
      ->List.take(30)
      ->Option.getExn
      ->List.toArray;

    <div
      style={ReactDOMRe.Style.make(~marginLeft="1rem", ~marginTop="1rem", ())}>
      <div style={ReactDOMRe.Style.make(~marginBottom="5rem", ())}>
        {ReasonReact.string("Your progress")}
      </div>
      <div
        style={ReactDOMRe.Style.make(
          ~marginLeft="1rem",
          ~marginTop="1rem",
          ~display="flex",
          ~justifyContent="center",
          ~alignItems="center",
          ~flexDirection="column",
          (),
        )}>
        <div
          style={ReactDOMRe.Style.make(
            ~marginBottom="1rem",
            ~fontSize="44px",
            (),
          )}>
          {ReasonReact.string("Weight")}
        </div>
        <ResponsiveContainer height={Px(400.)} width={Prc(90.)}>
          <AreaChart
            margin={"top": 0, "right": 0, "bottom": 0, "left": 0} data>
            <Area name="weight" dataKey="weight" stroke="#2078b4" />
            <CartesianGrid strokeDasharray="3 3" />
          </AreaChart>
        </ResponsiveContainer>
        <div
          style={ReactDOMRe.Style.make(
            ~marginTop="8rem",
            ~marginBottom="1rem",
            ~fontSize="44px",
            (),
          )}>
          {ReasonReact.string("Biceps")}
        </div>
        <ResponsiveContainer height={Px(400.)} width={Prc(90.)}>
          <LineChart
            margin={"top": 0, "right": 0, "bottom": 0, "left": 0} data>
            <Line name="left" dataKey="leftBicep" stroke="#2078b4" />
            <Line name="right" dataKey="rightBicep" stroke="#ff7f02" />
            <CartesianGrid strokeDasharray="3 3" />
            <Legend align=`center iconType=`circle />
          </LineChart>
        </ResponsiveContainer>
        <div
          style={ReactDOMRe.Style.make(
            ~marginTop="8rem",
            ~marginBottom="1rem",
            ~fontSize="44px",
            (),
          )}>
          {ReasonReact.string("Thigh")}
        </div>
        <ResponsiveContainer height={Px(400.)} width={Prc(90.)}>
          <LineChart
            margin={"top": 0, "right": 0, "bottom": 0, "left": 0} data>
            <Line name="left" dataKey="leftThigh" stroke="#2078b4" />
            <Line name="right" dataKey="rightThigh" stroke="#ff7f02" />
            <CartesianGrid strokeDasharray="3 3" />
            <Legend align=`center iconType=`circle />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>;

  | _ => <div />
  };
};
