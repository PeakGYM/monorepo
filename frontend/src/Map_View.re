type map;

[@bs.module "./map.js"] external loadMap: ('a, 'b) => map = "loadMap";

let windowHeight: unit => int = () => [%bs.raw {| window.innerHeight |}];

type location = {
  lat: float,
  lng: float,
};

let initialLocation = {lat: 50.0265321, lng: 19.9489974};

[@react.component]
let make = () => {
  let (location, setLocation) = React.useState(_ => initialLocation);
  let (modalOpen, setModalOpen) = React.useState(_ => true);
  let (simple, _full) = Gym_Query.use(~lat=location.lat, ~lng=location.lng);

  let gyms =
    switch (simple) {
    | Data(data) => data##gyms
    | _ => [||]
    };

  let position = [|location.lat, location.lng|];

  React.useEffect1(
    () => {
      loadMap(position, gyms) |> ignore;
      None;
    },
    [|gyms|],
  );

  let height = string_of_int(windowHeight() - 250) ++ "px";

  let updateLocation = loc => {
    let (lat, lng) = loc;

    setLocation(_ =>
      {lat: float_of_string(lat), lng: float_of_string(lng)}
    );
  };

  let picker =
    if (modalOpen) {
      <Coach_Picker gym={None}/>;
    } else {
      <div />;
    };

  <div style={ReactDOMRe.Style.make(~width="100%", ())}>
    <Map_Search setLocation={location => updateLocation(location)} />
    <div
      id="map-container"
      style={ReactDOMRe.Style.make(
        ~width="auto",
        ~height,
        ~display="block",
        (),
      )}
    />
    picker
  </div>;
};
