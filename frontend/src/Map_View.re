type map;
[@bs.module "./map.js"] external loadMap: 'a => map = "loadMap";

let lat = 50.0265321;
let lng = 19.9489974;

[@react.component]
let make = () => {
  let (simple, _full) = Gym_Query.use(~lat, ~lng);

  let coaches =
    switch (simple) {
    | Data(data) => data##gyms
    | _ => [||]
    };

  React.useEffect1(
    () => {
      Js.log(coaches);
      loadMap(coaches) |> ignore;
      None;
    },
    [|coaches|],
  );

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
