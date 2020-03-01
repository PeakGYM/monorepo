open BsRecharts;

[@react.component]
let make = () => {
  let (stats, _full) = Statistics_Query.use(~clientId="1");

  switch (stats) {
  | Data(result) =>
    let data =
      result##getAllFor
      ->List.fromArray
      ->List.take(5)
      ->Option.getExn
      ->List.toArray;

    <div
      style={ReactDOMRe.Style.make(~marginLeft="1rem", ~marginTop="1rem", ())}>
      <div style={ReactDOMRe.Style.make(~marginBottom="5rem", ())}>
        {ReasonReact.string("Your progress")}
      </div>
      <ResponsiveContainer height={Px(400.)} width={Prc(95.)}>
        <AreaChart margin={"top": 0, "right": 0, "bottom": 0, "left": 0} data>
          <Area name="Some bar" dataKey="leftBiceps" stroke="#2078b4" />
          <Area name="Some bar" dataKey="rightBiceps" stroke="#ff7f02" />
          <CartesianGrid strokeDasharray="3 3" />
          <Tooltip />
        </AreaChart>
      </ResponsiveContainer>
    </div>;
  /* <Legend align=`left iconType=`circle /> */
  | _ => <div />
  };
};
