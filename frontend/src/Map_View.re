type map;

[@bs.module "./map.js"] external loadMap: 'a => map = "loadMap";

let windowHeight: unit => int = () => [%bs.raw {| window.innerHeight |}];

type location = {
  lat: float,
  lng: float,
};

let initialLocation = {lat: 50.0265321, lng: 19.9489974};

[@react.component]
let make = () => {
  let (location, setLocation) = React.useState(_ => initialLocation);
  let (simple, _full) = Gym_Query.use(~lat=location.lat, ~lng=location.lng);

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

  let updateLocation = loc => {
    let [lat, lng] = loc;

    setLocation(_ => {lat, lng});
  };

  <div style={ReactDOMRe.Style.make(~width="100%", ())}>
    /* <Map_Search setLocation={location => updateLocation(location)} /> */
    <div
      id="map-container"
      style={ReactDOMRe.Style.make(
        ~width="auto",
        ~height,
        ~display="block",
        (),
      )}
    />
  </div>;
};
