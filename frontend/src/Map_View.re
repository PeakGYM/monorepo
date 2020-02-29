type map;
[@bs.module "./map.js"] external loadMap: 'a => map = "loadMap";

let lat = 50.0265321;
let lng = 19.9489974;

[@react.component]
let make = () => {
  let (simple, _full) = Gym_Query.use(~lat, ~lng);

  React.useEffect0(() => {
    loadMap("") |> ignore;
    None;
  });

  switch (simple) {
  | Data(data) => Js.log(data)
  | Error(_) => Js.log("EEEERRRR")
  | _ => Js.log("ERRROOOR")
  };

  <div
    id="map-container"
    style={ReactDOMRe.Style.make(
      ~width="auto",
      ~height="800px",
      ~position="relative",
      (),
    )}
  />;
};
