open Belt;

type places = array(place)
and place = {
  id: string,
  displayName: string,
  lat: string,
  lon: string,
};

let decode = json => {
  Json.Decode.(
    json
    |> array(json =>
         {
           id: json |> field("place_id", int) |> string_of_int,
           displayName:
             json
             |> field("display_name", string)
             |> Js.String.split(",")
             |> Array.reverse
             |> Array.map(_, Js.String.trim)
             |> Js.Array.joinWith(", "),
           lat: json |> field("lat", string),
           lon: json |> field("lon", string),
         }
       )
  );
};

let getPlaces = (~value) => {
  let acceptLanguage = "pl-PL";

  Api.get(
    {j|https://nominatim.openstreetmap.org/search?format=json&q=$value&limit=10&accept-language=$acceptLanguage|j},
  )
  |> Repromise.Rejectable.map(Result.map(_, decode));
};

/* let initialPlace = { */
/*   id: "pega", */
/*   displayName: "Pega", */
/*   lat: "50.0265321", */
/*   lon: "19.9489974", */
/* }; */

[@react.component]
let make = (~setLocation) => {
  let (value, setValue) = React.useState(() => "");
  let (places, setPlaces) = React.useState(() => [||]);

  let reloadPlaces = (search: string) => {
    getPlaces(~value=search)
    |> Repromise.Rejectable.andThen(
         fun
         | Result.Ok(places) => {
             setPlaces(_ => places);
             Repromise.Rejectable.resolved();
           }
         | Result.Error(_) => Repromise.Rejectable.resolved(),
       )
    |> ignore;
  };

  let handleChange = placeId => {
    let placeOpt =
      places->Array.keep(place => place.id == placeId)->Array.get(0);

    switch (placeOpt) {
    | Some(place) => setLocation((place.lat, place.lon))
    | None => ()
    };

    setValue(_ => placeId);
    ();
  };

  let handleSearch = search => {
    reloadPlaces(search);
  };

  let options =
    places->Array.map(place => {
      <Antd.Select.Option
        key={place.id} label={place.displayName} value={place.id}>
        {ReasonReact.string(place.displayName)}
      </Antd.Select.Option>
    });

  <div
    style={ReactDOMRe.Style.make(
      ~display="flex",
      ~justifyContent="center",
      ~alignItems="center",
      ~width="100%",
      ~height="5rem",
      (),
    )}>
    <Antd.Select
      placeholder="Enter location"
      value
      showSearch=true
      onChange=handleChange
      optionLabelProp="label"
      filterOption=false
      onSearch=handleSearch
      style={ReactDOMRe.Style.make(~width="90%", ())}>
      {ReasonReact.array(options)}
    </Antd.Select>
  </div>;
};
