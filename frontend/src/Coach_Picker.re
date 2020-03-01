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
      ->Array.map(coach => {
          <div
            key={coach##id}
            style={ReactDOMRe.Style.make(
              ~display="flex",
              ~justifyContent="left",
              ~position="fixed",
              ~marginBottom="0",
              ~marginTop="auto",
              ~padding="2rem",
              ~width="100%",
              ~height="55%",
              ~zIndex="1000",
              ~bottom=position,
              ~background="white",
              ~transition="all 0.2s",
              (),
            )}>
            <div
              style={ReactDOMRe.Style.make(
                ~display="flex",
                ~justifyContent="left",
                ~alignItems="center",
                ~height="10rem",
                (),
              )}>
              <img
                className=TW.(
                  [
                    Display(Flex),
                    FlexDirection(FlexCol),
                    Width(W32),
                    Height(H32),
                    BorderRadius(RoundedFull),
                  ]
                  |> make
                )
                src={coach##pictureUrl}
              />
              <div style={ReactDOMRe.Style.make(~marginLeft="2rem", ())}>
                {ReasonReact.string(coach##name)}
              </div>
            </div>
          </div>
        })

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
      ~background="white",
      ~borderRadius="5rem",
      ~transition="all 0.2s",
      (),
    )}>
    {ReasonReact.array(coaches)}
  </div>;
};
