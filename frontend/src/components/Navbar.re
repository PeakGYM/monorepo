[@react.component]
let make = () =>
  <nav
    style={ReactDOMRe.Style.make(~height="120px", ~bottom="0", ())}
    className=TW.(
      [
        Display(Flex),
        Position(Fixed),
        Padding(Py8),
        BoxShadow(ShadowXl),
        Width(WFull),
        AlignItems(ItemsCenter),
        BorderWidth(BorderT2),
      ]
      |> make
    )>
    <div
      onClick={_ => Router.go(Router.Map)}
      className=TW.(
        [
          Display(Flex),
          Flex(Flex1),
          Padding(P4),
          FlexDirection(FlexCol),
          JustifyContent(JustifyCenter),
        ]
        |> make
      )>
      <Icons.Global />
      <Text
        content={j|Mapa|j}
        style={ReactDOMRe.Style.make(~fontSize="24px", ())}
        className=TW.([TextAlign(TextCenter)] |> make)
      />
    </div>
    <div
      onClick={_ => Router.go(Router.Schedule)}
      className=TW.(
        [
          Display(Flex),
          Flex(Flex1),
          Padding(P4),
          FlexDirection(FlexCol),
          JustifyContent(JustifyCenter),
        ]
        |> make
      )>
      <Icons.Calendar />
      <Text
        content={j|Kalendarz|j}
        style={ReactDOMRe.Style.make(~fontSize="24px", ())}
        className=TW.([TextAlign(TextCenter)] |> make)
      />
    </div>
    <div
      className=TW.(
        [
          Display(Flex),
          Flex(Flex1),
          Padding(P4),
          FlexDirection(FlexCol),
          JustifyContent(JustifyCenter),
        ]
        |> make
      )>
      <Icons.Account />
      <Text
        content={j|Profil|j}
        style={ReactDOMRe.Style.make(~fontSize="24px", ())}
        className=TW.([TextAlign(TextCenter)] |> make)
      />
    </div>
    <div
      className=TW.(
        [
          Display(Flex),
          Flex(Flex1),
          Padding(P4),
          FlexDirection(FlexCol),
          JustifyContent(JustifyCenter),
        ]
        |> make
      )>
      <Icons.Chart />
      <Text
        content={j|Statystyki|j}
        style={ReactDOMRe.Style.make(~fontSize="24px", ())}
        className=TW.([TextAlign(TextCenter)] |> make)
      />
    </div>
  </nav>;