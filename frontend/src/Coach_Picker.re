[@react.component]
let make = (~gym) => {
  let position =
    switch (gym) {
    | Some(_) => "0px"
    | None => "-2000px"
    };

  let coaches =
    switch (gym) {
    | Some(_gym) =>
      _gym##coaches
      ->Array.map(coach => {<div> {ReasonReact.string(coach##name)} </div>})

    | None => [||]
    };

  <div
    style={ReactDOMRe.Style.make(
      ~position="fixed",
      ~marginBottom="0",
      ~marginTop="auto",
      ~width="100%",
      ~height="55%",
      ~zIndex="1000",
      ~bottom=position,
      ~background="white",
      ~borderRadius="5rem",
      ~transition="all 0.2s",
      (),
    )}>
    {ReasonReact.array(coaches)}
  </div>;
};
