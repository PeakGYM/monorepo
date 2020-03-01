[@react.component]
let make = (~gym) => {
  let position =
    switch (gym) {
    | Some(_gym) => "0px"
    | None => "-2000px"
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
      (),
    )}>
    {ReasonReact.string("Hello")}
  </div>;
};
