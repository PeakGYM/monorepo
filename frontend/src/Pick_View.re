[@react.component]
let make = (~id) => {
  <div className="pick-edit-or-log">
                                      <button
                                        onClick={_ => Router.go(DayLog(id))}
                                        className=TW.(
                                          [
                                            TextTransform(Uppercase),
                                            TextColor(TextBlue500),
                                            Padding(P4),
                                          ]
                                          |> make
                                        )>
                                        <Text
                                          content={j|Rozpocznij trening|j}
                                          style={ReactDOMRe.Style.make(
                                            ~fontSize="36px",
                                            (),
                                          )}
                                        />
                                      </button>
                                      <button
                                        onClick={_ => Router.go(DayEdit(id))}
                                        className=TW.(
                                          [
                                            TextTransform(Uppercase),
                                            TextColor(TextBlue500),
                                            Padding(P4),
                                          ]
                                          |> make
                                        )>
                                        <Text
                                          content={j|Edytuj trening|j}
                                          style={ReactDOMRe.Style.make(
                                            ~fontSize="36px",
                                            (),
                                          )}
                                        />
                                      </button>
                                    </div> /*Loguje workout - wysyalem planned jakie bylo a done, dodajesz*/ /*Edytuje workout - to nie dodaje do zrobione, edytuje planned, done - nie rusza*/;
};
