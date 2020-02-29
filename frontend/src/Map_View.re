type map;

[@bs.module "./map.js"] external loadMap: 'a => map = "loadMap";

let windowHeight: unit => int = () => [%bs.raw {| window.innerHeight |}];

let lat = 50.0265321;
let lng = 19.9489974;

[@react.component]
let make = () => {
  let (simple, _full) = Gym_Query.use(~lat, ~lng);

  let gyms =
    switch (simple) {
    | Data(data) => data##gyms
    | _ => [||]
    };

  React.useEffect1(
    () => {
      loadMap(gyms) |> ignore;
      None;
    },
    [|gyms|],
  );

  let height = string_of_int(windowHeight()) ++ "px";

  <div
    id="map-container"
    style={ReactDOMRe.Style.make(
      ~width="auto",
      ~height,
      ~position="relative",
      (),
    )}
  />;
};

