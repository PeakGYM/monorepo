[@react.component]
let make = (~id) => {
  let (data, _) = SetupVisit_Query.use(~coachId=id);
  let (date, setDate) = React.useState(() => MomentRe.momentNow());
  let date = date |> MomentRe.Moment.valueOf |> Time.toJSON;

  let (create, _full, _) =
    ApolloHooks.useMutation(
      ~variables=
        SetupVisit_Mutation.AddTestWorkout.makeVariables(
          ~from=date,
          ~clientId="1",
          ~coachId=id,
          (),
        ),
      SetupVisit_Mutation.AddTestWorkout.definition,
    );

  <div className=TW.([Display(Flex), Padding(P8)] |> make)>
    {switch (data) {
     | Data(data) =>
       data##getCoachesByIds
       ->Array.get(0)
       ->Option.map(c =>
           <div>
             <div
               style={ReactDOMRe.Style.make(
                 ~display="flex",
                 ~justifyContent="left",
                 ~alignItems="center",
                 ~paddingBottom="32px",
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
                 src={c##pictureUrl}
               />
               <div style={ReactDOMRe.Style.make(~marginLeft="2rem", ())}>
                 {React.string(c##name)}
               </div>
             </div>
             <div>
               <Text
                 content="Number: "
                 style={ReactDOMRe.Style.make(~marginRight="2rem", ())}
               />
               {React.string("+48 744 334 890")}
             </div>
             <div>
               <Calendar
                 onChange={date => setDate(_ => date)}
                 dateCellRender={_ => React.null}
               />
             </div>
             <div className=TW.([TextAlign(TextCenter)] |> make)>
               <div className=TW.([TextAlign(TextCenter)] |> make)>
                 <button
                   onClick={_ =>
                     create()
                     |> Js.Promise.then_(_ => {
                          Message.success({
                            "content": {j|Pomyślnie zaktualizowano dane|j},
                            "icon": <Ok />,
                            "top": 64,
                          });
                          Router.go(Router.Schedule);
                          Js.Promise.resolve();
                        })
                     |> ignore
                   }
                   className=TW.(
                     [
                       TextTransform(Uppercase),
                       TextColor(TextBlue500),
                       TextAlign(TextCenter),
                       Padding(Pb4),
                     ]
                     |> make
                   )>
                   <Text
                     content={j|PICK DATE|j}
                     style={ReactDOMRe.Style.make(~fontSize="36px", ())}
                   />
                 </button>
               </div>
             </div>
           </div>
         )
       ->Option.getWithDefault(React.null)
     | Loading => <Loader />
     | _ => React.null
     }}
  </div>;
};