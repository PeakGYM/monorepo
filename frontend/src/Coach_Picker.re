let trimName = (name: string) =>
  if (String.length(name) > 20) {
    String.sub(name, 0, 18) ++ "...";
  } else {
    name;
  };

[@react.component]
let make = (~gym) => {
  let position =
    switch (gym) {
    | Some(_) => "-5rem"
    | None => "-2000px"
    };

  let coaches =
    switch (gym) {
    | Some(_gym) =>
      _gym##coaches
      ->Array.map(coach => {
          <div
            key={coach##id}
            className=TW.(
              [BoxShadow(ShadowMd), BorderRadius(RoundedLg)] |> make
            )
            style={ReactDOMRe.Style.make(
              ~display="flex",
              ~justifyContent="left",
              ~width="100%",
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
                    Margin(Ml1),
                  ]
                  |> make
                )
                src={coach##pictureUrl}
              />
              <div style={ReactDOMRe.Style.make(~marginLeft="2rem", ())}>
                {ReasonReact.string(trimName(coach##name))}
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
      ~padding="2rem",
      ~background="white",
      ~transition="all 0.25s",
      ~overflowY="scroll",
      ~bottom=position,
      (),
    )}>
    {ReasonReact.array(coaches)}
    <div style={ReactDOMRe.Style.make(~height="10rem", ())} />
  </div>;
};
