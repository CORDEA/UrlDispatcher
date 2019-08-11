[![Build Status](https://app.bitrise.io/app/5b7322a3a0bb9a78/status.svg?token=IE8d-AHsx_1v2Hz15VTdMQ&branch=master)](https://app.bitrise.io/app/5b7322a3a0bb9a78)

# UrlDispatcher

Simple tool to dispatch the url. Use for open http/https urls, test deep links...etc.

<a href='https://play.google.com/store/apps/details?id=jp.cordea.urldispatcher&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
  <img width="200" alt='Get it on Google Play' src='https://play.google.com/intl/ja/badges/images/generic/en_badge_web_generic.png'/>
</a>

## Deploy rule

- Pull request
  - Deploy to bitrise.io
- develop branch
  - Deploy to alpha track of Google Play
- master branch
  - Deploy to beta track of Google Play
- tag
  - Deploy to production track of Google Play

## Bitrise configuration

[bitrise.yml](./example/bitrise.yml)