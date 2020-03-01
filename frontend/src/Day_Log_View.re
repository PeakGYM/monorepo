open Day_Query;

let defaultImg = "https://image.flaticon.com/icons/svg/1869/1869616.svg";

type u = [ | `kg | `s | `none];

let uTostring =
  fun
  | `kg => "kg"
  | `s => "s"
  | `none => "x";

module Block = {
  [@react.component]
  let make =
      (
        ~title,
        ~value,
        ~className,
        ~editable=false,
        ~onChange=(_, _) => (),
        ~type_=`none,
        ~serieId,
      ) => {
    let value =
      switch (value) {
      | Some(value) => value->string_of_int
      | None => "0"
      };
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
      {editable
         ? <Input
             value
             htmlType="number"
             onChange={event => {
               let value = event->ReactEvent.Form.target##value;
               let v =
                 switch (value) {
                 | "" => 0
                 | value => value->int_of_string
                 };
               onChange(serieId, Some(v));
             }}
             placeholder=value
           />
         : <Text
             className=TW.([TextAlign(TextCenter)] |> make)
             content={value ++ " " ++ uTostring(type_)}
             style={ReactDOMRe.Style.make(~fontSize="24px", ())}
           />}
    </div>;
  };
};

module Serie = {
  [@react.component]
  let make =
      (
        ~index,
        ~exercise,
        ~serie: Day_Query.serie,
        ~onRepeatChange,
        ~onRestChange,
        ~onWeightChange,
        ~onDone,
        ~update,
      ) => {
    let p = serie;
    let (checked, setChecked) =
      React.useState(_ =>
        exercise.doneSeries
        ->Array.keep(s => s.id === p.id)
        ->Array.get(0)
        ->Option.isSome
      );
    <>
      <div
        className=TW.(
          [Display(Flex), JustifyContent(JustifyBetween)] |> make
        )>
        <Text
          content={j|Series|j}
          style={ReactDOMRe.Style.make(~fontSize="36px", ())}
        />
        <Checkbox
          checked
          disabled=checked
          onChange={_ => {
            setChecked(change => !change);
            onDone(p.id, true);
            update() |> Js.Promise.then_(_ => {Js.Promise.resolve()}) |> ignore;
          }}>
          <Text content="Done" />
        </Checkbox>
      </div>
      <div
        className=TW.(
          [Display(Flex), Padding(P8), JustifyContent(JustifyBetween)]
          |> make
        )>
        <Block
          title={j|Reps|j}
          value={Some(p.reps)}
          className={TW.Margin(Mx0)}
          editable={!checked}
          type_=`none
          serieId={p.id}
          onChange=onRepeatChange
        />
        <Block
          title={j|Weight|j}
          value={p.weight}
          className={TW.Margin(Mx8)}
          editable={!checked}
          type_=`kg
          serieId={p.id}
          onChange=onWeightChange
        />
        <Block
          title={j|Rest|j}
          value={Some(p.rest)}
          className={TW.Margin(Mx0)}
          editable={!checked}
          type_=`s
          serieId={p.id}
          onChange=onRestChange
        />
      </div>
    </>;
  };
};

module Exercise = {
  [@react.component]
  let make =
      (
        ~exercise,
        ~update,
        ~onRepeatChange,
        ~onRestChange,
        ~onWeightChange,
        ~onDone,
      ) => {
    let (visible, setVisible) = React.useState(_ => false);
    let e = exercise.exercise;

    let allDone = exercise.plannedSeries->Array.length === 0;

    <div
      className=TW.(
        [BoxShadow(ShadowLg), BorderRadius(RoundedLg), Padding(P8)] |> make
      )>
      <div className=TW.([Display(Flex), FlexDirection(FlexRow)] |> make)>
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
            src={
              e->Option.map(e => e.imgurl)->Option.getWithDefault(defaultImg)
            }
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
            content={e->Option.map(e => e.name)->Option.getWithDefault("")}
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
                content={visible ? {j|See less|j} : {j|See more|j}}
                style={ReactDOMRe.Style.make(~fontSize="36px", ())}
              />
            </button>
          }>
          <Steps.Steps
            direction="vertical" current={exercise.doneSeries->Array.length}>
            {exercise.doneSeries
             ->Array.mapWithIndex((index, p) => {
                 <Steps.Step
                   title={
                     <Text
                       content={j|Series |j}
                       style={ReactDOMRe.Style.make(~fontSize="36px", ())}
                     />
                   }
                   description={
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
                         title={j|Reps|j}
                         value={Some(p.reps)}
                         className={TW.Margin(Mx0)}
                         type_=`none
                         serieId={p.id}
                       />
                       <Block
                         title={j|Weight|j}
                         value={p.weight}
                         className={TW.Margin(Mx8)}
                         type_=`kg
                         serieId={p.id}
                       />
                       <Block
                         title={j|Rest|j}
                         value={Some(p.rest)}
                         className={TW.Margin(Mx0)}
                         type_=`s
                         serieId={p.id}
                       />
                     </div>
                   }
                 />
               })
             ->React.array}
          </Steps.Steps>
          {exercise.plannedSeries
           ->Array.mapWithIndex((index, p) => {
               let index =
                 (exercise.doneSeries->Array.length + (index + 1))
                 ->string_of_int;
               exercise.doneSeries
               ->Array.keep(s => s.id === p.id)
               ->Array.get(0)
               ->Option.isSome
                 ? React.null
                 : <React.Fragment key={p.id}>
                     <Serie
                       index
                       exercise
                       serie=p
                       onRestChange
                       onRepeatChange
                       onWeightChange
                       onDone
                       update
                     />
                   </React.Fragment>;
             })
           ->React.array}
          <div className=TW.([TextAlign(TextCenter)] |> make)>
            <div className=TW.([TextAlign(TextCenter)] |> make)>
              <button
                onClick={_ =>
                  update()
                  |> Js.Promise.then_(_ => {
                       Message.success({
                         "content": {j|Succesfully updated|j},
                         "icon": <Ok />,
                         "top": 64,
                       });
                       Js.Promise.resolve();
                     })
                  |> ignore
                }
                className=TW.(
                  [
                    TextTransform(Uppercase),
                    TextColor(TextBlue500),
                    Padding(P4),
                    TextAlign(TextCenter),
                  ]
                  |> make
                )>
                <Text
                  content={j|Save|j}
                  style={ReactDOMRe.Style.make(~fontSize="36px", ())}
                />
              </button>
            </div>
          </div>
        </Collapse.Panel>
      </Collapse>
    </div>;
  };
};

type exerciseId = string;
type serieId = string;
type action =
  | UpdateReps(exerciseId, serieId, option(int))
  | UpdateRest(exerciseId, serieId, option(int))
  | UpdateWeight(exerciseId, serieId, option(int))
  | SetDone(exerciseId, serieId, bool);

let reducer = (state, action) =>
  switch (action) {
  | SetDone(exerciseId, serieId, value) => {
      ...state,
      exercises:
        state.exercises
        ->Array.map(e =>
            e.id === exerciseId && value === true
              ? {
                ...e,

                doneSeries:
                  e.doneSeries
                  ->Array.concat(
                      e.plannedSeries->Array.keep(s => s.id === serieId),
                    ),
              }
              : e
          ),
    }
  | UpdateRest(exerciseId, serieId, rest) => {
      ...state,
      exercises:
        state.exercises
        ->Array.map(e =>
            e.id === exerciseId
              ? {
                ...e,
                plannedSeries:
                  e.plannedSeries
                  ->Array.map(s =>
                      s.id === serieId
                        ? {...s, rest: rest->Option.getWithDefault(0)} : s
                    ),
              }
              : e
          ),
    }

  | UpdateWeight(exerciseId, serieId, weight) => {
      ...state,
      exercises:
        state.exercises
        ->Array.map(e =>
            e.id === exerciseId
              ? {
                ...e,
                plannedSeries:
                  e.plannedSeries
                  ->Array.map(s => s.id === serieId ? {...s, weight} : s),
              }
              : e
          ),
    }

  | UpdateReps(exerciseId, serieId, reps) => {
      ...state,
      exercises:
        state.exercises
        ->Array.map(e =>
            e.id === exerciseId
              ? {
                ...e,
                plannedSeries:
                  e.plannedSeries
                  ->Array.map(s =>
                      s.id === serieId
                        ? {...s, reps: reps->Option.getWithDefault(0)} : s
                    ),
              }
              : e
          ),
    }
  };

let toSerie = (s: Day_Query.serie) => {
  "id": s.id,
  "reps": s.reps,
  "rest": s.rest,
  "weight": s.weight,
};

let toMuscle =
  fun
  | `Arms => "Arms"
  | `Back => "Back"
  | `Chest => "Chest"
  | `Legs => "Legs"
  | `Shoulders => "Shoulders";

let toPayload = state => {
  "id": state.id,
  "name": state.name,
  "clientId": state.clientId,
  "coachId": state.coachId,
  "dateFrom": state.dateFrom->Time.toJSON,
  "dateTo": state.dateFrom->Time.toJSON,
  "inperson": false,
  "muscleGroup": state.muscleGroup->Array.map(toMuscle),
  "exercises":
    state.exercises
    ->Array.map(e =>
        {
          "trainingId": state.id,
          "exerciseId": e.id,
          "doneSeries": e.doneSeries->Array.map(toSerie),
          "plannedSeries": e.plannedSeries->Array.map(toSerie),
          "restAfter": e.restAfter,
        }
      ),
};

module View = {
  [@react.component]
  let make = (~workout as _w, ~defaultState) => {
    let (state, dispatch) = React.useReducer(reducer, defaultState);

    // let mutation = Day_Mutation.use(~training=toPayload(state));

    let (mutation, _simple, _full) =
      ApolloHooks.useMutation(
        ~variables=
          Day_Mutation.UpdateWorkout.makeVariables(
            ~training=toPayload(state),
            (),
          ),
        Day_Mutation.UpdateWorkout.definition,
      );

    <div>
      {state.exercises
       ->Array.map(e =>
           <Exercise
             key={e.id}
             onDone={(serieId, value) => {
               dispatch(SetDone(e.id, serieId, value))
             }}
             update={() => mutation()}
             exercise=e
             onRepeatChange={(serieId, value) =>
               dispatch(UpdateReps(e.id, serieId, value))
             }
             onRestChange={(serieId, value) =>
               dispatch(UpdateRest(e.id, serieId, value))
             }
             onWeightChange={(serieId, value) =>
               dispatch(UpdateWeight(e.id, serieId, value))
             }
           />
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

       workout
       ->Option.map(workout => <View workout defaultState=workout />)
       ->Option.getWithDefault(React.null);
     | Loading => <Loader />
     | _ => React.null
     }}
  </div>;
};