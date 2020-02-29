module Exercise = {
  [@react.component]
  let make = (~exercise) => {
    let (visible, setVisible) = React.useState(_ => false);

    exercise
    ->Option.map(e => {
        let done_ = e##plannedSeries;
        let allDone = e##plannedSeries->Array.length === 0;
        <div onClick={_ => setVisible(v => !v)}>
          <div
            className=TW.(
              [
                Display(Flex),
                FlexDirection(FlexRow),
                BoxShadow(Shadow2xl),
                BorderRadius(RoundedLg),
                Padding(P8),
              ]
              |> make
            )>
            <div>
              <img
                className=TW.(
                  [
                    Display(Flex),
                    FlexDirection(FlexCol),
                    Width(W64),
                    Height(H64),
                    Padding(Pr12),
                  ]
                  |> make
                )
                src={e##imgurl}
              />
            </div>
            <div
              className=TW.(
                [
                  Display(Flex),
                  AlignItems(ItemsCenter),
                  JustifyContent(JustifyBetween),
                  Flex(Flex1),
                ]
                |> make
              )>
              <Text
                content=e##name
                style={ReactDOMRe.Style.make(
                  ~fontSize="36px",
                  ~fontWeight="600",
                  (),
                )}
              />
              {allDone ? <Ok /> : React.null}
            </div>
          </div>
          <Collapse bordered=false activeKey={visible ? [|"1"|] : [||]}>
            <Collapse.Panel
              key="1"
              extra={
                <button
                  className=TW.(
                    [TextTransform(Uppercase), TextColor(TextBlue500)]
                    |> make
                  )>
                  <Text
                    content={j|Zobacz więcej|j}
                    style={ReactDOMRe.Style.make(~fontSize="48px", ())}
                  />
                </button>
              }>
              <div />
            </Collapse.Panel>
          </Collapse>
        </div>;
      })
    ->Option.getWithDefault(React.null);
  };
};

[@react.component]
let make = (~id) => {
  let (data, _) = Day_Query.use(~id);

  <div className=TW.([Padding(P8)] |> make)>
    {switch (data) {
     | Data(data) =>
       let workout = data##workout;
       let workout =
         Some({
           "id": "1",
           "exercises": [|
             {
               "id": "1",
               "exercise":
                 Some({
                   "id": "1",
                   "name": {j|Biceps - na ławeczce|j},
                   "imgurl": "https://image.flaticon.com/icons/svg/1869/1869616.svg",
                   "plannedSeries": [||],
                 }),
             },
           |],
         });
       workout
       ->Option.map(workout =>
           <div>
             {workout##exercises
              ->Array.map(e => <Exercise key={e##id} exercise={e##exercise} />)
              ->React.array}
           </div>
         )
       ->Option.getWithDefault(React.null);

     | Loading => <Loader />
     | _ => React.null
     }}
  </div>;
};