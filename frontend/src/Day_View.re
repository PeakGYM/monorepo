module Block = {
  [@react.component]
  let make = (~title, ~text, ~className) =>
    <div
      className=TW.(
        [
          Display(Flex),
          FlexDirection(FlexCol),
          BoxShadow(ShadowMd),
          BorderRadius(RoundedLg),
          JustifyContent(JustifyCenter),
          Padding(P4),
          Flex(Flex1),
          className,
        ]
        |> make
      )>
      <Text
        content=title
        className=TW.([Margin(Mb8), TextAlign(TextCenter)] |> make)
        style={ReactDOMRe.Style.make(~fontSize="36px", ())}
      />
      <Text
        className=TW.([TextAlign(TextCenter)] |> make)
        content=text
        style={ReactDOMRe.Style.make(~fontSize="24px", ())}
      />
    </div>;
};

module Exercise = {
  [@react.component]
  let make = (~exercise, ~onChange) => {
    let (visible, setVisible) = React.useState(_ => true);

    exercise
    ->Option.map(e => {
        let done_ = e##plannedSeries;
        let allDone = e##plannedSeries->Array.length === 0;
        <div
          className=TW.(
            [BoxShadow(ShadowLg), BorderRadius(RoundedLg), Padding(P8)]
            |> make
          )>
          <div
            className=TW.([Display(Flex), FlexDirection(FlexRow)] |> make)>
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
                  onClick={_ => setVisible(v => !v)}
                  className=TW.(
                    [
                      TextTransform(Uppercase),
                      TextColor(TextBlue500),
                      Padding(P4),
                    ]
                    |> make
                  )>
                  <Text
                    content={
                      visible ? {j|Zobacz mniej|j} : {j|Zobacz więcej|j}
                    }
                    style={ReactDOMRe.Style.make(~fontSize="48px", ())}
                  />
                </button>
              }>
              {e##doneSeries
               ->Array.mapWithIndex((index, p) => {
                   let index = (index + 1)->string_of_int;
                   <>
                     <Text
                       content={j|Seria $index|j}
                       style={ReactDOMRe.Style.make(~fontSize="48px", ())}
                     />
                     <div
                       className=TW.(
                         [
                           Display(Flex),
                           Padding(P8),
                           JustifyContent(JustifyBetween),
                         ]
                         |> make
                       )>
                       <Block
                         title={j|Powtórzenia|j}
                         text={p##reps->string_of_int}
                         className={TW.Margin(Mx0)}
                       />
                       <Block
                         title={j|Ciężar|j}
                         text={p##weight->string_of_int ++ " kg"}
                         className={TW.Margin(Mx8)}
                       />
                       <Block
                         title={j|Odpoczynek|j}
                         text={p##rest->string_of_int ++ " s"}
                         className={TW.Margin(Mx0)}
                       />
                     </div>
                   </>;
                 })
               //  <div> <Text content={p##weight->string_of_int} /> </div>
               //  <div> <Text content={p##rest->string_of_int} /> </div>
               ->React.array}
              {e##plannedSeries
               ->Array.mapWithIndex((index, p) => {
                   let index =
                     (e##doneSeries->Array.length + (index + 1))
                     ->string_of_int;
                   <>
                     <Text
                       content={j|Seria $index|j}
                       style={ReactDOMRe.Style.make(~fontSize="48px", ())}
                     />
                     <div
                       className=TW.(
                         [
                           Display(Flex),
                           Padding(P8),
                           JustifyContent(JustifyBetween),
                         ]
                         |> make
                       )>
                       <Block
                         title={j|Powtórzenia|j}
                         text={p##reps->string_of_int}
                         className={TW.Margin(Mx0)}
                       />
                       <Block
                         title={j|Ciężar|j}
                         text={p##weight->string_of_int ++ " kg"}
                         className={TW.Margin(Mx8)}
                       />
                       <Block
                         title={j|Odpoczynek|j}
                         text={p##rest->string_of_int ++ " s"}
                         className={TW.Margin(Mx0)}
                       />
                     </div>
                   </>;
                 })
               //  <div> <Text content={p##weight->string_of_int} /> </div>
               //  <div> <Text content={p##rest->string_of_int} /> </div>
               ->React.array}
            </Collapse.Panel>
          </Collapse>
        </div>;
      })
    ->Option.getWithDefault(React.null);
  };
};

let reducer = (state, action) =>
  switch (action) {
  | _ => state
  };
module View = {
  [@react.component]
  let make = (~workout, ~defaultState) => {
    let mutation = Day_Mutation.use();

    let (state, dispatch) = React.useReducer(reducer, defaultState);
    <div>
      {workout##exercises
       ->Array.map(e =>
           <Exercise key={e##id} exercise={e##exercise} onChange=Js.log />
         )
       ->React.array}
    </div>;
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
                   "plannedSeries": [|
                     {"id": "1", "reps": 12, "rest": 30, "weight": 70},
                   |],

                   "doneSeries": [|
                     {"id": "1", "reps": 12, "rest": 30, "weight": 70},
                   |],
                 }),
             },
           |],
         });
       workout
       ->Option.map(workout => <View workout defaultState=data##workout />)
       ->Option.getWithDefault(React.null);

     | Loading => <Loader />
     | _ => React.null
     }}
  </div>;
};